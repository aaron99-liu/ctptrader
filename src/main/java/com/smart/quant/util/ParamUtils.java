package com.smart.quant.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.smart.quant.annotation.Validation;
import com.smart.quant.model.Result;

public class ParamUtils {
	private static final Logger logger = LoggerFactory.getLogger(ParamUtils.class);
	
	public static Result validateParam(Object paramObject) {
		Result result = new Result();
		if(paramObject == null) {
			result.setRetcode(-100);
			result.setMessage("");
			return result;
		}
		Class<?> clazz = paramObject.getClass();
		for(Field field : clazz.getFields()) {
			Validation validationAnno = field.getAnnotation(Validation.class);
			if(validationAnno != null) {
				String fieldName = field.getName();
				String getter = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Method getMethod = clazz.getMethod(getter);
					Class<?> fieldType = getMethod.getReturnType();
					Object fieldValue = getMethod.invoke(paramObject);
					if(!validationAnno.nullable() && fieldValue == null) {
						result.setRetcode(-101);
						result.setMessage("param class=[" + clazz.getName() + "], field=[" + fieldName + "] can not be null");
						return result;
					}
					if(fieldValue instanceof String) {						
						String[] values = validationAnno.checkValues();
						List<String> valueList = Arrays.asList(values);
						if(valueList != null && valueList.size() > 0 && !valueList.contains(fieldValue)) {
							result.setRetcode(-101);
							result.setMessage("param class=[" + clazz.getName() + "], field=[" + fieldName + "] must be null or in(" + Arrays.toString(values) + ")");
							return result;
						}
					}else if(fieldType.equals(int.class) || fieldType.equals(Integer.class)) {						
						String[] values = validationAnno.checkValues();
						List<Integer> valueList = new ArrayList<Integer>();
						for(String val : values) {
							valueList.add(Integer.valueOf(val));
						}
						if(valueList != null && valueList.size() > 0 && fieldValue != null && !valueList.contains(fieldValue)) {
							result.setRetcode(-101);
							result.setMessage("param class=[" + clazz.getName() + "], field=[" + fieldName + "] must be null or in(" + Arrays.toString(values) + ")");
							return result;
						}
					}else if(fieldType.equals(long.class) || fieldType.equals(Long.class)) {						
						String[] values = validationAnno.checkValues();
						List<Long> valueList = new ArrayList<Long>();
						for(String val : values) {
							valueList.add(Long.valueOf(val));
						}
						if(valueList != null && valueList.size() > 0 && fieldValue != null && !valueList.contains(fieldValue)) {
							result.setRetcode(-101);
							result.setMessage("param class=[" + clazz.getName() + "], field=[" + fieldName + "] must be null or in(" + Arrays.toString(values) + ")");
							return result;
						}
					}else {
						logger.info("param valid failed, field type is not compatible");
						
					}
				} catch (NoSuchMethodException e) {
					logger.error("validate param error,class=[" + clazz.getName() + "], getter=[" + getter + "] is not exist", e);
				} catch (SecurityException e) {
					logger.error("validate param error,class=[" + clazz.getName() + "], getter=[" + getter + "] can not access", e);
				} catch (InvocationTargetException e) {
					logger.error("validate param error,class=[" + clazz.getName() + "], getter=[" + getter + "] can not be invoke", e);
				} catch (IllegalAccessException e) {
					logger.error("validate param error,class=[" + clazz.getName() + "], getter=[" + getter + "] can not be access", e);
				}
			}
		}
		result.setRetcode(0);
		result.setMessage("");
		return result;
	}
}
