package com.superwei.utils.trans;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author weidongge
 * @program shebao-fenhao
 * @description 接口事务处理
 * @create 2021-09-29 20:36
 */
@Component
public class TransactionalComponent {

    public interface Cell {

        void run() throws Exception;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void required(Cell cell) throws Exception {

        cell.run();
    }
}
