import java.util.ArrayList;
import java.util.Iterator;

public class TestMain {
    public static  ArrayList<ArrayList<Double>> segData = new ArrayList<ArrayList<Double>>();
    static boolean isDataOver = true;
    static int index = 0;
    public static void main(String []args){
        segData.add(CaculatePress.initializationSegData());
        while (isDataOver != true) {
            ArrayList<ArrayList<String>> dataString = readData();

            ArrayList<ArrayList<Double>> result = new ArrayList<>();

            for (int i = 0; i < dataString.size(); i++) {
                CaculatePress caculatePress = new CaculatePress(dataString.get(i));
                addSegData(caculatePress);//更新各个液柱段数据
                result.add(caculateResult(caculatePress));//依据segData计算最终输出结果
            }
            writeResult(result);
        }
    }
    public static ArrayList<ArrayList<String>> readData(){

    }
//    public static ArrayList<ArrayList<Double>> initialization(){//初始化程序
//
//    }
    public static void addSegData(CaculatePress caculatePress){//更新液柱段
        if((segData.get(0).get(0)-caculatePress.segmentData().get(0)) > 0){
            double ratio = (segData.get(0).get(0)-caculatePress.segmentData().get(0))/segData.get(0).get(0);
            segData.get(0).set(0,segData.get(0).get(0)-caculatePress.segmentData().get(0));
            segData.get(0).set(1,segData.get(0).get(1)*ratio);
            segData.get(0).set(2,segData.get(0).get(2)*ratio);
            segData.add(caculatePress.segmentData());
        }else {
            double count = segData.get(0).get(0);
            int j = 1;
            for (;;j++){
                count += segData.get(j).get(0);
                if (count >= caculatePress.segmentData().get(0))
                    break;
            }
            double ratio = (count - caculatePress.segmentData().get(0))/segData.get(j).get(0);
            segData.get(0).set(0,count-caculatePress.segmentData().get(0));
            segData.get(0).set(1,segData.get(j).get(1)*ratio);
            segData.get(0).set(2,segData.get(j).get(2)*ratio);
            for(int a = 1;j+a<segData.size();a++){
                segData.get(a).set(0,segData.get(j+a).get(0));
                segData.get(a).set(1,segData.get(j+a).get(1));
                segData.get(a).set(2,segData.get(j+a).get(2));
            }
            for(;j>0;j--){
                segData.remove(segData.size()-1);
            }
            segData.add(caculatePress.segmentData());
        }

    }
    public static ArrayList<Double> caculateResult(CaculatePress caculatePress){//计算压力结果
        ArrayList<Double> segResult = new ArrayList<>();
        double staticPress = 0,press = 0,friction = 0;
        for (int i = 0;i < segData.size();i++){
            staticPress += segData.get(i).get(1);
            friction += segData.get(i).get(2);
        }
        press = caculatePress.wellheadPressure+staticPress-friction;
        segResult.add(staticPress);
        segResult.add(friction);
        segResult.add(press);
        return segResult;

    }
    public static void writeResult(ArrayList<ArrayList<Double>> result){//写入结果

    }
}
