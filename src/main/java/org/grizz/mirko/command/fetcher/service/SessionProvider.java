package org.grizz.mirko.command.fetcher.service;

import com.crozin.wykop.sdk.Application;
import com.crozin.wykop.sdk.Session;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class SessionProvider {
    private final static String PUBLIC_KEY = "mirko.key.public.";
    private final static String PRIVATE_KEY = "mirko.key.secret.";
    private final static String USER_KEY = "mirko.key.user.";
    private Iterator<KeyPair> keysIterator;

    @Autowired
    private Environment env;

    @PostConstruct
    private void init() {
        List<KeyPair> keysList = Lists.newArrayList();
        String publicKey;
        String privateKey;
        String userKey;
        int counter = 1;

        log.info("Loading app-keys...");
        while ((publicKey = env.getProperty(PUBLIC_KEY + counter)) != null &&
                (privateKey = env.getProperty(PRIVATE_KEY + counter)) != null &&
                (userKey = env.getProperty(USER_KEY + counter)) != null) {
            keysList.add(new KeyPair(publicKey, privateKey, userKey));
            counter++;
        }
        keysIterator = Iterables.cycle(keysList).iterator();

        log.info("Loaded {} app-keys", counter - 1);
    }

    public synchronized Session getSession() {
        KeyPair keys = keysIterator.next();
        Application app = new Application(keys.publicKey, keys.getPrivateKey());
        return app.openSession();
    }

    @Getter
    @AllArgsConstructor
    private class KeyPair {
        private final String publicKey;
        private final String privateKey;
        private final String userKey;
    }
}
