package theSacred.vfx.combat.unique;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSacred.util.UC;

public class PlayerFadeEffect extends AbstractGameEffect {
    private float halfTime;
    public PlayerFadeEffect(float dur) {
        duration = startingDuration = dur;
        halfTime = dur/2f;
    }

    @Override
    public void update() {
        Color tint = UC.p().tint.color;
        if(duration > halfTime) {
            tint.a = Interpolation.exp10In.apply(0, 1f, (duration / halfTime) - 1f);
        } else {
            tint.a = Interpolation.exp10In.apply(0f, 1f, 1f - duration / halfTime);
        }

        duration -= UC.gt();

        if(duration <= 0) {
            isDone = true;
            tint.a = 1f;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) { }

    @Override
    public void dispose() { }
}
