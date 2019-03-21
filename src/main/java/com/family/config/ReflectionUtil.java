package com.family.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 
 * 项目名称：GMBTools
 * 类名称：ReflectionUtil
 * 类描述：  TODO(反射工具类)
 * 开发单位: 北京天耀宏图科技有限公司
 * 创建人：FanWY
 * 创建时间：2014-11-16 下午3:45:58
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version v1.0
 *
 */
public abstract class ReflectionUtil {
     
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    /**
     * 缓存方法
     */
    private static final Map<Class<?>, Method[]>  METHODS_CACHEMAP = new HashMap<Class<?>, Method[]>();
     
    /**
     * 反射 取值、设值,合并两个对象(Field same only )
     * 

     */
    public static <T> void copyProperties(T fromobj, T toobj,
            String... fieldspec) {
        for (String filename : fieldspec) {
            Object val = ReflectionUtil.invokeGetterMethod(fromobj, filename);
            ReflectionUtil.invokeSetterMethod(toobj, filename, val);
        }
         
    }
     
    /**
     * 调用Getter方法
     * 
     * @param obj
     *            对象
     * @param propertyName
     *            属性名
     * @return
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, null, null);
    }
    
    /**
     * 调用Getter方法
     * 
     * @param obj
     *            对象
     * @param propertyName
     *            属性名
     * @return
     */
	@SuppressWarnings("rawtypes")
    public static List<Object> invokeGetterMethod(List list,Object obj, String propertyName) {
    	List<Object> rList = new ArrayList<Object>();
    	for (Object object : list) {
    		if(object!=null)
    		rList.add(invokeGetterMethod(object,propertyName));
		}
        return rList;
    }
     
    /**
     * 调用Setter方法,不指定参数的类型
     * 
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }
     
    /**
     * 调用Setter方法,指定参数的类型
     * 
     * @param obj
     * @param propertyName  字段名
     * @param value
     * @param propertyType
     *            为空，则取value的Class
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value, Class<?> propertyType) {
        value = handleValueType(obj,propertyName,value);
        propertyType = propertyType != null ? propertyType : value.getClass();        
        if(propertyType == ArrayList.class){
        	propertyType = List.class;
        }
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class<?>[] { propertyType },new Object[] { value });
    }

    /**
     * 
     * @Title: handleValueType
     * @Description: TODO(获取对象类型)
     * @param @param obj
     * @param @param propertyName
     * @param @param value
     * @param @return    设定文件
     * @return Object    返回类型
     * @throws
     */
    public static Object handleValueType(Object obj, String propertyName,
            Object value){
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<?> argsType = value.getClass();
        Class<?> returnType = obtainAccessibleMethod(obj, getterMethodName).getReturnType();
        if(argsType == returnType){
            return value;
        }
        if (returnType == Boolean.class) {
        	if("1".equalsIgnoreCase(value.toString()) || "".equalsIgnoreCase(value.toString())){
        		value = true;
        	}else{
        		value = false;
        	}
        } else if (returnType == Long.class) {
            value = Long.valueOf(value.toString());
        }else if(returnType == Date.class){
            try {            	
                value =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString());
            } catch (Exception e) {
                logger.error("类型转型Timpestap-->Date时，发生错误! " + e.getMessage() + "("+value.toString()+")");
            }
        } else if (returnType == Short.class) {
            value = Short.valueOf(value.toString());
        } else if (returnType == BigDecimal.class) {
            value = BigDecimal.valueOf(Long.valueOf(value.toString()));
        } else if (returnType == BigInteger.class) {
            value = BigInteger.valueOf(Long.valueOf(value.toString()));
        } else if(returnType == String.class){
            value = String.valueOf(value);
        }else if(returnType == Integer.class){
            value = Integer.valueOf(value.toString());
        }else if(returnType==Double.class){
        	value = Double.valueOf(value.toString());
        }else if(returnType==Float.class){
        	value = Float.valueOf(value.toString());
        }
        return value;
    }
     
    /**
     * 直接调用对象方法，忽视private/protected修饰符
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param params
     * @return
     */
    public static Object invokeMethod(final Object obj,
            final String methodName, final Class<?>[] parameterTypes,
            final Object[] args) {
        Method method = obtainAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
        	throw new IllegalArgumentException(
                "Devkit: Could not find method [" + methodName
                        + "] on target [" + obj + "]."); }
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**
     * 循环向上转型，获取对象的DeclaredMethod,并强制设置为可访问 如向上转型到Object仍无法找到，返回null
     * 
     * 用于方法需要被多次调用的情况，先使用本函数先取得Method,然后调用Method.invoke(Object obj,Object...
     * args)
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method obtainAccessibleMethod(final Object obj,
            final String methodName, final Class<?>... parameterTypes) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            Method method = null;
            try {
                method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
            	logger.error(e.getMessage());
            } catch (SecurityException e) {
            	logger.error(e.getMessage());
            }
        }
        return null;
    }
     
    /**
     * 不能确定方法是否包含参数时，通过方法名匹配获得方法
     * 
     * @param obj
     * @param methodName
     * @return
     */
    public static Method obtainMethod(final Object obj, final String methodName) {
        Class<?> clazz = obj.getClass();
        Method[] methods = METHODS_CACHEMAP.get(clazz);
        if (methods == null) { // 尚未缓存
            methods = clazz.getDeclaredMethods();
            METHODS_CACHEMAP.put(clazz, methods);
        }
        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
         
    }
     
    /**
     * 直接读取对象属性值 忽视private/protected修饰符，不经过getter函数
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object obtainFieldValue(final Object obj,
            final String fieldName) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        Object retval = null;
        try {
            retval = field.get(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return retval;
         
    }
     
    /**
     * 直接设置对象属性值 忽视private/protected修饰符，不经过setter函数
     * 
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName,
            final Object value) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * 循环向上转型，获取对象的DeclaredField,并强制设为可访问 如向上转型Object仍无法找到，返回null
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field obtainAccessibleField(final Object obj,
            final String fieldName) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            try {            	
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义，向上转型
            } catch (SecurityException e) {
                // Field不在当前类定义，向上转型
            }
        }
        return null;
    }

}
