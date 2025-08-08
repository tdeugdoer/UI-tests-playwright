package utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    @UtilityClass
    public class ExceptionMessage {
        public final String NEGATIVE_PRICE_ERROR = "Новое значение цены не может быть отрицательным";
        public final String SLIDER_OUT_OF_BOUNDS_ERROR = "Невозможно переместить слайдер за допустимые границы";
        public final String REDUCTION_MIN_PRICE_ERROR = "Невозможно уменьшить минимальную цену";
        public final String INCREASE_MAX_PRICE_ERROR = "Невозможно увеличить максимальную цену";
        public final String UNABLE_ADD_TO_CART_ERROR = "Невозможно увеличить максимальную цену";

    }

}
