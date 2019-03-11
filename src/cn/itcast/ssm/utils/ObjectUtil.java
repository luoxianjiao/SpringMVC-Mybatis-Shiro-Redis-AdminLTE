package cn.itcast.ssm.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import java.beans.Introspector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

public class ObjectUtil
{
  private static final Map<Class<?>, Method[]> cache = new WeakHashMap();

  static String decapitalize(String fieldName) {
    return Introspector.decapitalize(fieldName);
  }

  public static final String objToBase64Str(Object obj) {
    Base64 b64 = new Base64();
    byte[] bytes = b64.encode(objToBytes(obj));
    b64 = null;
    return new String(bytes);
  }

  public static final Object base64StrToObject(String b64Str) {
    Base64 b64 = new Base64();
    byte[] bytes = b64.decode(b64Str.getBytes());
    return bytesToObj(bytes);
  }

  public static final Map<String, Object> describe(Object target) {
    Map retMap = new HashMap();
    List retList = new LinkedList();
    Method[] methods = (Method[])(Method[])cache.get(target.getClass());
    if (methods == null) {
      methods = target.getClass().getMethods();
      cache.put(target.getClass(), methods);
    }
    for (int i = 0; i < methods.length; i++) {
      String method = methods[i].getName();
      if ((method.indexOf("set") == 0) || (method.indexOf("get") == 0))
        retList.add(method.substring(3, method.length()));
      else if (method.indexOf("is") == 0) {
        retList.add(method.substring(2, method.length()));
      }
    }
    Collections.sort(retList);
    Object[] props = retList.toArray();
    retList.clear();
    for (int i = 0; i < props.length - 1; i++) {
      if (props[i].equals(props[(i + 1)])) {
        String prop = decapitalize(props[i].toString());
        retMap.put(prop, getValue(prop, target));
      }
    }
    retList = null;
    return retMap;
  }

  public static final void populate(Object obj, Map<String, Object> map) {
    Set s = map.entrySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      String key = "";
      Object o = null;      
      Map.Entry me = (Map.Entry)it.next();
      String tstr = null;
      Class type = null;
      try {
        key = me.getKey().toString();
        o = me.getValue();
        if (o == null) {
          continue;
        }
        setValue(key, obj, o);
      } catch (IllegalArgumentException e) {
    	  
        type = getType(key, obj);
        tstr = type.toString();
        if (tstr.equals(Integer.class.toString()))
          setValue(key, obj, Integer.valueOf(o.toString()));
        else if (tstr.equals(Long.class.toString()))
          setValue(key, obj, Long.valueOf(o.toString()));
        else if (tstr.equals(Float.class.toString()))
          setValue(key, obj, Float.valueOf(o.toString()));
        else if (tstr.equals(Double.class.toString()))
          setValue(key, obj, Double.valueOf(o.toString()));
        else if (tstr.equals(Short.class.toString()))
          setValue(key, obj, Short.valueOf(o.toString()));
        else if (tstr.equals(Byte.class.toString()))
          setValue(key, obj, Byte.valueOf(o.toString()));
        else if (tstr.equals(Date.class.toString())) {
          if ((o instanceof Date)) {
            setValue(key, obj, (Date)o);
          } else {
            long time = ((Double)o).longValue();
            setValue(key, obj, new Date(time));
          }
        } else if (tstr.equals(Timestamp.class.toString())) {
          if ((o instanceof Timestamp)) {
            setValue(key, obj, (Date)o);
          } else {
            long time = ((Double)o).longValue();
            setValue(key, obj, new Timestamp(time));
          }
        }
        else throw e;
      }
      
    }
  }

  public static final Class<Object> getType(String property, Object target)
  {
    Class ret = Object.class;
    property = "set" + property;

    Method[] methods = (Method[])(Method[])cache.get(target.getClass());
    if (methods == null) {
      methods = target.getClass().getMethods();
      cache.put(target.getClass(), methods);
    }

    for (int i = 0; i < methods.length; i++)
    {
      if (!property.equalsIgnoreCase(methods[i].getName())) {
        continue;
      }
      Class[] paramClass = methods[i].getParameterTypes();
      if (paramClass.length == 1) {
        return paramClass[0];
      }

    }

    return ret;
  }

  public static final Object getValue(String property, Object target)
  {
    String get = "get" + property;
    String is = "is" + property;

    Method[] methods = (Method[])(Method[])cache.get(target.getClass());
    if (methods == null) {
      methods = target.getClass().getMethods();
      cache.put(target.getClass(), methods);
    }

    for (int i = 0; i < methods.length; i++)
    {
      if ((!get.equalsIgnoreCase(methods[i].getName())) && (!is.equalsIgnoreCase(methods[i].getName())))
        continue;
      try {
        return methods[i].invoke(target, (Object[])null);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }

    return null;
  }

  public static <T> T jsonToObjectWithJackson(String json, Class<T> daoImpClass)
  {
    try
    {
      return ObjectMapperCreator.getInstance().getObjectMapper().readValue(json, daoImpClass);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String objectToJsonWithJackson(Object dataObject)
  {
    try
    {
      return ObjectMapperCreator.getInstance().getObjectMapper().writeValueAsString(dataObject);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static final void setFieldValue(Object target, String field, Object value)
  {
    try
    {
      Class obj = target.getClass();
      Field[] fields = obj.getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        fields[i].setAccessible(true);
        if (field.equals(fields[i].getName())) {
          fields[i].set(target, value);
          break;
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static final void setValue(String property, Object target, Object value) {
    property = "set" + property;
    Method[] methods = (Method[])(Method[])cache.get(target.getClass());
    if (methods == null) {
      methods = target.getClass().getMethods();
      cache.put(target.getClass(), methods);
    }
    for (int i = 0; i < methods.length; i++) {
      if (!property.equalsIgnoreCase(methods[i].getName()))
        continue;
      Class[] paramClass = methods[i].getParameterTypes();
      if (paramClass.length != 1) continue;
      try {
        methods[i].invoke(target, new Object[] { value });
      } catch (IllegalArgumentException ille) {
        throw ille;
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  public static final byte[] objToBytes(Object obj)
  {
    ByteArrayOutputStream bao = null;
    try
    {
      bao = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bao);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
      byte[] arrayOfByte = bao.toByteArray();
      return arrayOfByte;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
    finally
    {
      try
      {
        if (bao != null) {
          bao.close();
          bao = null;
        } 
      } catch (IOException localIOException2) {
    	  	  
      }
    }
	    
  }

  public static final Object bytesToObj(byte[] bytes)
  {
    ByteArrayInputStream bai = null;
    try
    {
      bai = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bai);
      Object obj = ois.readObject();
      ois.close();
      ois = null;
      Object localObject1 = obj;
      return localObject1;
    }
    catch (Exception e)
    {
//      throw new RuntimeException(e);
    	e.printStackTrace();
    	return null;
    } finally {
      try {
        if (bai != null) {
          bai.close();
          bai = null;
        } } catch (IOException localIOException1) {
        	localIOException1.printStackTrace();
      }
    }
    
  }

  public static final Object objectClone(Object originObj)
  {
    ByteArrayOutputStream bao = null;
    ByteArrayInputStream bai = null;
    try
    {
      bao = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bao);
      oos.writeObject(originObj);
      oos.flush();
      oos.close();
      bai = new ByteArrayInputStream(bao.toByteArray());
      ObjectInputStream ois = new ObjectInputStream(bai);
      Object obj = ois.readObject();
      ois.close();
      oos = null;
      ois = null;
      Object localObject1 = obj;
      return localObject1;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (bao != null) {
          bao.close();
          bao = null;
        }
        if (bai != null) {
          bai.close();
          bai = null;
        } } catch (IOException localIOException2) {
      }
    }
    
  }

  public static final int sizeOf(Object obj)
  {
    ByteArrayOutputStream bao = null;
    try
    {
      bao = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bao);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
      int i = bao.size();
      return i;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      int localIOException = 0;
      return localIOException;
    }
    finally
    {
      try
      {
        if (bao != null) {
          bao.close();
          bao = null;
        } } catch (IOException localIOException2) {
      }
    }    
  }

  public static String objectToXml(Object dataObject)
  {
    return XStreamCreater.getInstance().objectToXml(dataObject);
  }

  public static Object xmlToObject(String xml) {
    return XStreamCreater.getInstance().xmlToObject(xml);
  }

  public static String objectToJsonWithXStream(Object dataObject)
  {
    return XStreamCreater.getInstance().objectToJsonWithXStream(dataObject);
  }

  public static Object jsonToObjectWithXStream(String json)
  {
    return XStreamCreater.getInstance().jsonToObjectWithXStream(json);
  }

  private static class XStreamCreater
  {
    private static XStreamCreater instance = new XStreamCreater();

    public static XStreamCreater getInstance()
    {
      return instance;
    }

    public String objectToXml(Object dataObject) {
      XStream xtm = new XStream();

      String dd = xtm.toXML(dataObject);
      return dd;
    }

    public Object xmlToObject(String xml) {
      XStream xtm = new XStream();
      Object o = xtm.fromXML(xml);
      return o;
    }

    public String objectToJsonWithXStream(Object dataObject) {
      XStream xtm = new XStream(new JettisonMappedXmlDriver());

      xtm.setMode(1001);

      String dd = xtm.toXML(dataObject);
      return dd;
    }

    public Object jsonToObjectWithXStream(String json) {
      XStream xtm = new XStream(new JettisonMappedXmlDriver());

      Object o = xtm.fromXML(json);
      return o;
    }
  }

  private static class ObjectMapperCreator
  {
    private volatile ObjectMapper objectMapper;
    private static ObjectMapperCreator instance = new ObjectMapperCreator();

    public static ObjectMapperCreator getInstance()
    {
      return instance;
    }

    public ObjectMapper getObjectMapper() {
      if (this.objectMapper == null) {
        this.objectMapper = new ObjectMapper();
      }
      return this.objectMapper;
    }
  }
}