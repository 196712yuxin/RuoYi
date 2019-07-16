package com.panda.project.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.panda.project.domain.SysMenu;
import com.panda.project.service.SysMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/sysmenu/menu")
public class DubboController {

    @Reference(version = "0.0.0")
    SysMenuService sysMenuService;

    private String prefix = "system/menu";
    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menu")
    @ResponseBody
    public List<SysMenu> menuTreeData()
    {
        List<SysMenu> tree = sysMenuService.selectSysMenuAll();
        return tree;
    }
    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap)
    {
        int chack = 0;
      //  sysMenuService.selectMenuById(menuId);
       // mmap.put("menu", sysMenuService.selectMenuById(menuId));
        Jedis  jedis = new Jedis ("192.168.0.101",6379);
        jedis.expire("key", 20);
        jedis.set("menuID",String.valueOf(menuId));
        jedis.sadd("menuid", String.valueOf(menuId));
        String menuID= jedis.get("menuID");
        System.out.println(menuID);
        System.out.println(jedis.smembers("menuid"));
        if(jedis.smembers("sysMenu").size()>0){
            List<SysMenu> menus = JSON.parseObject(jedis.smembers("sysMenu").toString(), new TypeReference<List<SysMenu>>() {});
            Set<String> menuset   = jedis.smembers("sysMenu");
          System.out.println(jedis.ttl("sysMenu"));
            for(String jsonString :menuset){
                JSONObject jsonobject = JSONObject.parseObject(jsonString);
                SysMenu menu = jsonobject.toJavaObject(SysMenu.class);
                System.out.println(menu.getMenuId());
                if(menu.getMenuId()==menuId){
                    mmap.put("menu", menu);
                    chack = 0;
                    break;
                }else {
                    chack = 1;
                }
            }
            if(chack==1){
                mmap.put("menu", sysMenuService.selectMenuById(menuId));
            }
        }else {
            mmap.put("menu", sysMenuService.selectMenuById(menuId));
        }
        return prefix + "/edit";
    }

    public void saveRedis(String key, String value, Long expireSecond) {
            Jedis jedisCluster = new Jedis("192.168.0.101", 6379);
            boolean keyExist = jedisCluster.exists(key);
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
            if (keyExist) {
                jedisCluster.del(key);
            }
            jedisCluster.set(key, value, "NX", "EX", expireSecond);
        }
    }
