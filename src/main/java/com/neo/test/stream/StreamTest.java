package com.neo.test.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Huangcz
 * @date 2018/10/31 10:51
 * @desc
 */
public class StreamTest {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );


        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                // 按照交易额从低到高排序
                .sorted(Comparator.comparing(Transaction::getValue))
                // 转为集合
                .collect(Collectors.toList());


        List<String> cityList = transactions.stream()
                // 从交易中取出所有城市
                .map(transaction -> transaction.getTrader().getCity())
                // 去重
                .distinct()
                // 转换为
                .collect(Collectors.toList());

        // 查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> traderList = transactions.stream()
                // 从交易中提取所有的交易员
                .map(Transaction::getTrader)
                // 进选择位于剑桥的交易员
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                // 确保没有重复
                .distinct()
                // 对生成的交易员流按照姓名进行排序
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + " " + n2);



        int sum1 = transactions.stream().mapToInt(Transaction::getValue).sum();


        System.out.println(sum1);

    }

}
