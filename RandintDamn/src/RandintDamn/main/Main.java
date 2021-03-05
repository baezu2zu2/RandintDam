package RandintDamn.main;

import RandintDamn.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("운빨똥망겜 플러그인이 활성화 되었습니다!");
        Bukkit.getPluginManager().registerEvents(new Event(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("운빨똥망겜 플러그인이 비활성화 되었습니다!");
    }
}
