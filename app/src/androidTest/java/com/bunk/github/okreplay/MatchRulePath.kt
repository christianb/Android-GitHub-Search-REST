package com.bunk.github.okreplay

import okreplay.MatchRule
import okreplay.Request

private val TAG = MatchRulePath::class.java.simpleName

class MatchRulePath : MatchRule {
    override fun isMatch(actual: Request, recorded: Request): Boolean {
        val recordedPath = recorded.url().url().path
        val realPath = actual.url().url().path

        return realPath.startsWith(recordedPath)
    }
}