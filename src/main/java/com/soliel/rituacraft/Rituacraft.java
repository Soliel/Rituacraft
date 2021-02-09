package com.soliel.rituacraft;

import com.soliel.rituacraft.client.ClientProxy;
import com.soliel.rituacraft.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("rituacraft")
public class Rituacraft
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "rituacraft";

    private static Rituacraft instance;

    public final CommonProxy proxy;

    public Rituacraft() {
        instance = this;

        this.proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        this.proxy.initialize();
        this.proxy.attachLifecycle(FMLJavaModLoadingContext.get().getModEventBus());
        this.proxy.attachEventHandlers(MinecraftForge.EVENT_BUS);
    }

    public static Rituacraft getInstance() {
        return instance;
    }

    public static CommonProxy getProxy() {
        return getInstance().proxy;
    }


}
