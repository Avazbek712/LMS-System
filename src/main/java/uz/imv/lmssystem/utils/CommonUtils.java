package uz.imv.lmssystem.utils;

public class CommonUtils {

    public static<T> T getOrDef(T value, T def){
        return value == null ? def : value;
    }

}
