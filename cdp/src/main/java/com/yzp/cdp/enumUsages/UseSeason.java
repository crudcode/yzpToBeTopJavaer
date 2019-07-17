package com.yzp.cdp.enumUsages;



public class UseSeason {
    public String getChineseSeason(Season season)
    {
        StringBuffer result = new StringBuffer();
        switch (season)
        {
            case SPRING:
                result.append("[中文：春天，枚举常量："+season.name()+",数据:"+season.getCode()+"]");
                break;
            case SUMMER:
                result.append("[中文：夏天，枚举常量："+season.name()+",数据:"+season.getCode()+"]");
                break;
            case AUTUMN:
                result.append("[中文：秋天，枚举常量："+season.name()+",数据:"+season.getCode()+"]");
                break;
            case WINTER:
                result.append("[中文：冬天，枚举常量："+season.name()+",数据:"+season.getCode()+"]");
                break;
            default:
                result.append("地球没有的季节"+season.name());
                break;
        }
        return result.toString();
    }
    public void doSomething()
    {
        for(Season s: Season.values())
        {
            System.out.println(getChineseSeason(s));
        }
        //System.out.println(getChineseSeason(3));//此处已经编译不通过，保证了类型安全
    }

    public static void main(String[] args) {
        UseSeason u = new UseSeason();
        u.doSomething();
    }
}
