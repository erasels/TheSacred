package theSacred.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.commons.lang3.math.NumberUtils;
import theSacred.TheSacred;
import theSacred.util.TextureLoader;

public class BarrierVfx extends AbstractGameEffect {
    private static final String path = "spellBarrier";

    private boolean ending;

    private Texture barrier;
    private int barrierwidth, barrierheight;
    private Hitbox hb;

    private final float spacing = 1.4F;

    private static final float DURATION = 2.5f;

    public BarrierVfx(Hitbox hb, Color col) {
        this.hb = hb;
        this.ending = false;
        this.rotation = MathUtils.random() * (float)Math.PI * 2;
        duration = startingDuration = DURATION;

        this.barrier = TextureLoader.getTexture(TheSacred.makeVfxPath(path) + ".png");
        this.scale = hb.height * spacing / barrier.getHeight();
        this.barrierwidth = barrier.getWidth();
        this.barrierheight = barrier.getHeight();

        this.color = col.cpy();
        this.color.a = 0F;
    }

    @Override
    public void update() {
        rotation += Gdx.graphics.getDeltaTime() * 10;

        if(!ending) {
            color.a += NumberUtils.min(Gdx.graphics.getDeltaTime() * 2, 1f - color.a);
        } else {
            if(color.a > 0F) {
                color.a -= NumberUtils.min(Gdx.graphics.getDeltaTime() * 2, color.a);
            } else {
                isDone = true;
            }
        }

        duration -= Gdx.graphics.getDeltaTime();
        if(!ending && duration <= startingDuration/2f) {
            end();
        }
    }

    public void end() {
        ending = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(barrier, hb.cX - barrierwidth / 2F, hb.cY - barrierheight / 2F, barrierwidth / 2f, barrierheight / 2f, barrierwidth, barrierheight, scale, scale, rotation, 0, 0, barrier.getWidth(), barrier.getHeight(), false, false);
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {
        //barrier.dispose();
    }
}