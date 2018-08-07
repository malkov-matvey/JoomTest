package name.malkov.joomtest;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    @NonNull
    public static <T> List<T>mergeLists(List<T> l1, List<T> l2) {
        if (l1 == null && l2 == null) return Collections.emptyList();
        else if (l1 == null) return Collections.unmodifiableList(l2);
        else if (l2 == null) return Collections.unmodifiableList(l1);
        else {
            final List<T> res = new ArrayList<>(l2.size() + l1.size());
            res.addAll(l1);
            res.addAll(l2);
            return res;
        }
    }
}
