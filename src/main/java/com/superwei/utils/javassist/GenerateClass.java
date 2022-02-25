package com.superwei.utils.javassist;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author weidongge
 * @date 2022-02-24 11:19
 */
public class GenerateClass {

    private GenerateClass() {}

    /**
     * 根据源类和需要展示源类的属性列表生成指定类名的class
     *
     * @param className     生成类名
     * @param sourceClass   源类
     * @param showFieldList 展示列名称列表
     * @return java.lang.Class<?>
     * @author apple
     * @date 2022/2/25
     */
    public static Class<?> initMyClass(String className, Class<?> sourceClass, List<String> showFieldList) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        //根据类名构建类
        CtClass ctClass = pool.makeClass(className);
        ClassFile ccFile = ctClass.getClassFile();
        ConstPool constpool = ccFile.getConstPool();
        //新增类注解
        AnnotationsAttribute classAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        Annotation lombokData = new Annotation(Data.class.getName(), constpool);
        classAttr.addAnnotation(lombokData);
        ccFile.addAttribute(classAttr);
        //创建属性
        for (Field field : sourceClass.getDeclaredFields()) {
            //final属性不处理、未带ExcelProperty注解的属性不处理
            if (Modifier.isFinal(field.getModifiers()) || Objects.isNull(field.getAnnotation(ExcelProperty.class))) {
                continue;
            }
            //生成属性
            CtField ctField = new CtField(pool.get(field.getGenericType().getTypeName()), field.getName(), ctClass);
            ctField.setModifiers(Modifier.PRIVATE);
            ctClass.addField(ctField);
            //添加属性注解
            FieldInfo fieldInfo = ctField.getFieldInfo();
            //判断是否在展示列表中
            if (showFieldList.contains(fieldInfo.getName())) {
                ExcelProperty oldAnnotation = field.getAnnotation(ExcelProperty.class);
                String[] value = oldAnnotation.value();
                AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
                Annotation autowired = new Annotation(ExcelProperty.class.getName(), constpool);
                ArrayMemberValue arrayMemberValue  = new ArrayMemberValue(constpool);
                MemberValue[] elements = new MemberValue[1];
                elements[0] = new StringMemberValue(value[0], constpool);
                arrayMemberValue.setValue(elements);
                autowired.addMemberValue("value", arrayMemberValue);
                fieldAttr.addAnnotation(autowired);
                fieldInfo.addAttribute(fieldAttr);
            }else {
                //将未在展示列表的属性隐藏
                AnnotationsAttribute fieldAttr3 = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
                Annotation autowired3 = new Annotation(ExcelIgnore.class.getName(), constpool);
                fieldAttr3.addAnnotation(autowired3);
                fieldInfo.addAttribute(fieldAttr3);
            }
        }
        //写入项目路径下
        ctClass.writeFile(TestFileUtil.getPath());
        return ctClass.toClass();
    }

}
