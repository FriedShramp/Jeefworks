package com.jeefle.jeefworks.registry;

import com.jeefle.jeefworks.Jeefworks;
import com.tterrag.registrate.providers.ProviderType;

public class JWDatagen { public static void init() {
        Jeefworks.REGISTRATE.addDataGenerator(ProviderType.LANG, Lang::init);
    }
}
