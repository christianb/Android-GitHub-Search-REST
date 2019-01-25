package com.bunk.github.okreplay

import android.Manifest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import com.bunk.github.data.OkReplayInterceptorSingleton
import okreplay.*
import org.junit.rules.RuleChain

class OkReplayConfigurator(
    aclass: Class<out Any>
) {

    private var okReplayConfig: OkReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(AndroidTapeRoot(getInstrumentation().context, aclass))
        .defaultMatchRule(ComposedMatchRule.of(MatchRules.method, MatchRulePath()))
        .defaultMode(TapeMode.READ_WRITE) // or TapeMode.READ_ONLY
        .sslEnabled(true)
        .interceptor(OkReplayInterceptorSingleton)
        .build()

    fun getRuleChain(): RuleChain =
        RuleChain
            .outerRule(GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .around(PermissionRule(okReplayConfig))
            .around(RecorderRule(okReplayConfig))
}