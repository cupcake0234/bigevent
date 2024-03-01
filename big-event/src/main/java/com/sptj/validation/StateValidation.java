package com.sptj.validation;

import com.sptj.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    /**
     *
     * @param value 将要校验的数据
     * @param context
     * @return false 为不通过，true为校验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //
        if(value == null){
            return false;
        }

        if(value.equals("已发布") || value.equals("草稿")){
            return true;
        }

        return false;
    }
}
