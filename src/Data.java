import java.util.ArrayList;
import java.util.List;

public class Data {
    static double diameter;//直径
    static int vertica_depth;//垂深
    static int slant_depth;//斜深
    public int parameter_1;//纯液密度/混砂液体积密度
    public int parameter_2;//纯液粘度/混砂液视密度
    public String time;//时间
    public double wellheadPressure;//井口压力
    public double displacement;//排出排量
    public int liquidType;//液体类型；
    public double sandConcentration;//砂浓度
    public int DRE;//减阻率

    public List<List<Object>> data ;
}
