package theSacred.vfx.general;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theSacred.TheSacred;

public class RunAnimationEffect extends AbstractGameEffect {
    private String key;
    public enum ANIS {
       IDLE, GUARDA, GUARDB, HITLOW, HITHIGH, FAILURE, LOWKICK, RODWHACK, RODSTAB, RODSLASH, BACKFLIP, WINA, WINB, DOWNED, DOWNIDLE
    }

    public RunAnimationEffect(String animation_key) {
        key = animation_key;
    }

    public RunAnimationEffect(ANIS ani) {
        switch(ani) {
            case IDLE:
                key = "idle";
                break;
            case GUARDA:
                key = "guardA";
                break;
            case GUARDB:
                key = "guardB";
                break;
            case HITLOW:
                key = "hitLow";
                break;
            case HITHIGH:
                key = "hitHigh";
                break;
            case FAILURE:
                key = "failure";
                break;
            case LOWKICK:
                key = "lowKick";
                break;
            case RODWHACK:
                key = "rodWhack";
                break;
            case RODSTAB:
                key = "rodStab";
                break;
            case RODSLASH:
                key = "rodSlash";
                break;
            case BACKFLIP:
                key = "backflip";
                break;
            case WINA:
                key = "winA";
                break;
            case WINB:
                key = "winB";
                break;
            case DOWNED:
                key = "downed";
                break;
            case DOWNIDLE:
                key = "downedIdle";
                break;
        }
    }

    @Override
    public void update() {
        TheSacred.runAnimation(key);
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    @Override
    public void dispose() {
    }
}
