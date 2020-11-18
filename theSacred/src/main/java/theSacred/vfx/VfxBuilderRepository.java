package theSacred.vfx;

import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSacred.TheSacred;
import theSacred.util.TextureLoader;
import theSacred.util.VfxBuilder;

public class VfxBuilderRepository {
    public static AbstractGameEffect ofudaShot(Hitbox hb, float angle, float ofudaAngle) {
        return new VfxBuilder(TextureLoader.getTexture(TheSacred.makeVfxPath("ofuda.png")), hb.cX + hb.width, hb.cY, 0.8f)
                .setScale(0.5f)
                .setAngle(ofudaAngle)
                .velocity(angle, 4000f)
                .build();
    }
}
