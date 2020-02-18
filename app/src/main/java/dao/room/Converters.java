package dao.room;

import androidx.room.TypeConverter;

import domain.Rubro;

public class Converters {
        @TypeConverter
        public static Rubro fromStringRubro(String rubro) {
            return Rubro.valueOf(rubro);
        }
        @TypeConverter
        public static String ruroToSring(Rubro rubro) {
            return rubro == null ? null : rubro.toString();
        }
}
