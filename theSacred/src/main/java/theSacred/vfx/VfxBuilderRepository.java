package theSacred.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import theSacred.util.UC;
import theSacred.util.VfxBuilder;

public class VfxBuilderRepository {
    public static AbstractGameEffect ofudaShot(Hitbox hb, float angle, float ofudaAngle) {
        return new VfxBuilder(UC.getTexture("vfx", "ofuda"), hb.cX + hb.width, hb.cY, 0.8f)
                .setScale(0.5f)
                .setAngle(ofudaAngle)
                .playSoundAt(0.1f, "MONSTER_BOOK_STAB_1")
                .velocity(angle, 2500f)
                .build();
    }

    public static AbstractGameEffect curvedDanmakuShot(Hitbox originHb, Hitbox targetHb, Color col) {
        return new VfxBuilder(UC.getTexture("vfx", "danmaku"), originHb.cX + originHb.width, originHb.cY, 0.4f)
                .setScale(0.66f)
                .setColor(col)
                .useAdditiveBlending()
                .playSoundAt(0, "ATTACK_MAGIC_FAST_3")
                .arc(originHb.cX + originHb.width, originHb.cY, targetHb.cX, targetHb.cY, MathUtils.random(Settings.HEIGHT * 0.33f, Settings.HEIGHT * 0.66f))
                .andThen(0.2f)
                .triggerVfxAt(0, 5, (x, y) -> new DarkOrbActivateEffect(targetHb.cX, targetHb.cY))
                .build();
    }
}
