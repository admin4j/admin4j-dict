package com.admin4j.dict.anno;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对方父子单据列表的映射工具类
 *
 * @author andanyang
 * @since 2022/10/10 10:04
 */
public class DictMapUtils {

    private DictMapUtils() {
    }

    /**
     * 对象翻译
     * 使用场景： 返回订单主单信息包含items信息的场景
     *
     * @param mains            主单列表
     * @param mainKeyFunction  主单主键。用户查找items
     * @param getItemsFunction 通过主单主键列表获取Items列表
     * @param itemKeyFunction  items外键字段（关联主单主键）
     * @param setItemConsumer  设置主单信息
     * @param <M>              主单class
     * @param <K>              主单与items关联的字段类型
     * @param <S>              item 类型
     */
    public static <M, K, S> void mapItem(List<M> mains,
                                         Function<M, K> mainKeyFunction,
                                         Function<List<K>, List<S>> getItemsFunction,
                                         Function<S, K> itemKeyFunction,
                                         BiConsumer<M, S> setItemConsumer) {

        // 获取主键列表
        List<K> keys = mains.stream().map(mainKeyFunction).distinct().collect(Collectors.toList());
        if (keys.isEmpty()) {
            return;
        }
        // 通过主键获取 子单数据，
        List<S> items = getItemsFunction.apply(keys);
        if (items.isEmpty()) {
            return;
        }

        // 主单找子单

        for (M main : mains) {

            for (S item : items) {

                // 主单主键和子单外键相等
                if (Objects.equals(mainKeyFunction.apply(main), itemKeyFunction.apply(item))) {
                    setItemConsumer.accept(main, item);
                }
            }
        }
    }
}
