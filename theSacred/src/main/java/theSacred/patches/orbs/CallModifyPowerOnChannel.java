package theSacred.patches.orbs;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import theSacred.orbs.interfaces.DamageAndBlockModifyOrb;

@SpirePatch(clz = ChannelAction.class, method = "update")
public class CallModifyPowerOnChannel {
    @SpirePostfixPatch
    public static void callMP(ChannelAction __instance, AbstractOrb ___orbType) {
        if(___orbType instanceof DamageAndBlockModifyOrb && __instance.isDone) {
            AbstractDungeon.onModifyPower();
        }
    }
}
