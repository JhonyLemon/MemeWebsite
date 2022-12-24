package pl.jhonylemon.memewebsite.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private Validator(){}

    public static boolean isIdValid(Long id){
        return (id != null && id > 0);
    }

    public static boolean isStringValid(String value){
        return (value != null && !value.isBlank());
    }

    public static void checkPermission(List<String> currentPermissions, Map<String,FunctionHolder> permissionsToCheck){
        for(String permission : currentPermissions){
            FunctionHolder holder = permissionsToCheck.get(permission);
            if(holder!=null){
                holder.execute();
                return;
            }
        }
    }

}
