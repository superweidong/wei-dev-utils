package com.superwei.utils.trans;

/**
 * @author weidongge
 * @program shebao-fenhao
 * @description 事务冲突解决util
 * @create 2021-09-29 20:35
 */
public class TransactionalUtils {

    private static volatile TransactionalComponent transactionalComponent;

    private static synchronized TransactionalComponent getTransactionalComponent() {
        if (transactionalComponent == null) {
            // 从容器中获取 transactionalComponent
            transactionalComponent = SpringApplicationContextUtil.getContext().getBean(TransactionalComponent.class);
        }
        return transactionalComponent;
    }

    public static void required(TransactionalComponent.Cell cell) throws Exception {
        getTransactionalComponent().required(cell);
    }

}
