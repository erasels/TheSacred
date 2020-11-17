package theSacred.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BetterWrathParticleEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float dur_div2;
    private TextureAtlas.AtlasRegion img;

    public BetterWrathParticleEffect(Hitbox hb) {
        img = ImageMaster.GLOW_SPARK;
        duration = MathUtils.random(1.3F, 1.8F);
        scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        color = new Color(MathUtils.random(0.5F, 1.0F), 0.0F, MathUtils.random(0.0F, 0.2F), 0.0F);
        x = hb.cX + MathUtils.random(-hb.width - 30.0F * Settings.scale, hb.width + 30.0F * Settings.scale);
        y = hb.cY + MathUtils.random(-hb.height -10.0F * Settings.scale, hb.height + 10.0F * Settings.scale);
        x -= (float)img.packedWidth / 2.0F;
        y -= (float)img.packedHeight / 2.0F;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        rotation = MathUtils.random(-8.0F, 8.0F);
    }

    public void update() {
        if (duration > dur_div2) {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - dur_div2) / dur_div2);
        } else {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / dur_div2);
        }

        vY += Gdx.graphics.getDeltaTime() * 40.0F * Settings.scale;
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F) {
            isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y + vY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, scale * 0.8F, (0.1F + (dur_div2 * 2.0F - duration) * 2.0F * scale) * Settings.scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
