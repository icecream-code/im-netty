package com.iqy.im.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator 校验工具类
 *
 * @author iQiyi
 * @since 2020-03-26
 */
public class ValidatorUtil {
    private static final Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 对实体类进行校验
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws RuntimeException 校验不通过抛出CustomException
     */
    public static void validate(Object object, Class<?>... groups) throws RuntimeException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new RuntimeException(constraint.getMessage());
        }
    }

}
