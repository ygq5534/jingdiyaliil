import java.util.ArrayList;

public class CaculatePress {
    static final double PI = 3.1415926;
    public static final double g = 9.81;//重力加速度

    public static final double diameter = 0.115;//直径
    public static final int vertica_depth = 2300;//垂深
    public static final int slant_depth = 4193;//斜深
    //public static final double cos = vertica_depth/slant_depth;
    public int parameter_1;//纯液密度/混砂液体积密度
    public int parameter_2;//纯液粘度/混砂液视密度
    public String time;//时间
    public double wellheadPressure;//井口压力
    public double displacement;//排出排量
    public int liquidType;//液体类型；
    public double sandConcentration;//砂浓度
    public double DRE;//减阻率
    public double Mix_Density;//混砂液密度

    public boolean isDisplacementPure = true;
    public double segStaticPress;
    public double segFriction;
    public double h;//△h

    public static double PH = 2300;//纯液高度


    CaculatePress(){
        super();
    }
    CaculatePress(ArrayList<String> dataRow){
        wellheadPressure = Double.parseDouble(dataRow.get(1));
        displacement = Double.parseDouble(dataRow.get(2));
        liquidType = Integer.parseInt(dataRow.get(3));
        parameter_1 = Integer.parseInt(dataRow.get(4));
        parameter_2 = Integer.parseInt(dataRow.get(5));
        sandConcentration = Double.parseDouble(dataRow.get(6));
        DRE = Double.parseDouble(dataRow.get(7));

    }


    public ArrayList<Double> segmentData(){
        ArrayList<Double> segData = new ArrayList<>();
        caculateH();
        caculateF();
        caculateStaticPress();
        segData.add(h);
        segData.add(segStaticPress);
        segData.add(segFriction);
        segData.add(wellheadPressure);
        return segData;
    }

    public static ArrayList<Double> initializationSegData(){
        double staticPress = 0.000001*1005*g*2300;
        ArrayList<Double> initializationData = new ArrayList<>();
        initializationData.add((double)2300);
        initializationData.add(staticPress);
        initializationData.add((double)0);
        return initializationData;
    }
/**
 * 计算排除液体高度
 */

    public void caculateH(){//计算排出液体高度
        double displacementSecond = displacement/60;//每秒排出量
        h = displacementSecond/(PI*(diameter*diameter/4));
    }
/**
 * 计算沿程摩阻
 */
    public void caculateF(){//计算沿程摩阻
        if (liquidType == 1)
            segFriction = 0.000001*caculatef()*caculatePVD();
        else
            segFriction = DRE*0.000001*caculatef()*caculatePVD();
    }
    public double caculatef(){//计算清水摩阻系数
        if(caculateNre()>=3000){
            return 0.3164/Math.pow(caculateNre(),0.25);
        }else {
            return 64/caculateNre();
        }
    }
    public double caculateNre(){
        return 1000*diameter*caculateV()/0.001;
    }
    public double caculateV(){
        return 4*displacement/60*PI*diameter*diameter;
    }
    public double caculatePVD(){
        return 1000*caculateV()*caculateV()/2*diameter;
    }
/**
 * 计算静液柱压力
 */

    public void caculateStaticPress(){
        if (liquidType == 1)
            segStaticPress = 0.000001*parameter_1*g*h;
        else
            segStaticPress = 0.000001*mixDiameter()*g*h;
    }
    public double mixDiameter(){
        return parameter_1+sandConcentration*(1-parameter_1/parameter_2);
    }
}
