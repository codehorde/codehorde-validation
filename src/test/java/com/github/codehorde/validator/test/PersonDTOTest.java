package com.github.codehorde.validator.test;

import com.github.codehorde.validator.dto.PersonDTO;
import com.github.codehorde.validator.Util;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by baomingfeng at 2017-11-15 19:50:16
 */
public class PersonDTOTest {

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testEnumString() {
        PersonDTO person = new PersonDTO();
        person.setDept("X");
        Set<ConstraintViolation<PersonDTO>> constraintViolations = validator.validate(person);
        Util.echo(constraintViolations);
        Assert.assertTrue(constraintViolations.size() != 0);
    }

    @Test
    public void testIn_Number() {
        PersonDTO person = new PersonDTO();
        person.setScore(6);
        Set<ConstraintViolation<PersonDTO>> constraintViolations = validator.validate(person);
        Util.echo(constraintViolations);
        Assert.assertTrue(constraintViolations.size() != 0);
    }

    @Test
    public void testIn_String() {
        PersonDTO person = new PersonDTO();
        person.setGender("man");
        Set<ConstraintViolation<PersonDTO>> constraintViolations = validator.validate(person);
        Util.echo(constraintViolations);
        Assert.assertTrue(constraintViolations.size() != 0);
    }

    @Test
    public void testURL() {
        PersonDTO person = new PersonDTO();
        //person.setPicUrl("http://img.alicdn.com/imgextra/i4/TB2qFGohpXXXXbxXpXXXXXXXXXX-350475995.jpg");
        person.setPicUrl("ftp://img.alicdn.com/imgextra/i4/TB2qFGohpXXXXbxXpXXXXXXXXXX-350475995.jpg");
        Set<ConstraintViolation<PersonDTO>> constraintViolations = validator.validate(person);
        Util.echo(constraintViolations);
        Assert.assertTrue(constraintViolations.size() != 0);
    }

}
