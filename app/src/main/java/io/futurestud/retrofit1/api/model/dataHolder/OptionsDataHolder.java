package io.futurestud.retrofit1.api.model.dataHolder;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionsDataHolder {
    private Long id;
    private Integer count;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionsDataHolder)) return false;
        OptionsDataHolder that = (OptionsDataHolder) o;
        return Objects.equals(getId(), that.getId());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
