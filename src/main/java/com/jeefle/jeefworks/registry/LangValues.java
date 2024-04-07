package com.jeefle.jeefworks.registry;

import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.jeefle.jeefworks.registry.Lang.*;

public class LangValues {
    public static void init(RegistrateLangProvider provider){
        replace(provider, "jeefworks.volcanus.tooltip1", "§7Legally distinct from other notable Pyrotheic Ovens");
        replace(provider, "jeefworks.blaze_vent", "§7These seem remarkably similar pyrotheum heating hatches from gt++? \n Ah no these are patented blaze vents! old jeefworks recipe!");
    }
}
