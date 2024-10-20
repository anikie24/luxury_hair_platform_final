package za.ac.cput.factory;

import za.ac.cput.domain.Tips;

public class TipsFactory {
    public static Tips buildTips(int id, byte[] image) {
        return new Tips.Builder()
                .setId(id)
                .setImage(image)
                .build();
    }
}
