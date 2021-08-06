package com.couplesdating.couplet.domain.useCase.idea

class DecorateIdeaUseCaseImpl : DecorateIdeaUseCase {

    override fun getIdeasToDecorate(description: String): List<String> {
        val descriptionWords = description.replace(".", "").split(" ")
        return descriptionWords.filter { word ->
            WORDS_TO_DECORATE.contains(word.toLowerCase())
        }
    }

    private companion object {
        private val WORDS_TO_DECORATE = arrayOf(
            "partner",
            "kink",
            "massage",
            "naked",
            "hot",
            "have sex",
            "sex",
            "oral",
            "bite",
            "kiss",
            "nipples",
            "lick",
            "suck",
            "spank",
            "suffocate",
            "tie-up",
            "butthole",
            "genitals",
            "jerk off",
            "dildo",
            "strip-tease",
            "shower",
            "cunnilingus",
            "tongue",
            "lips",
            "blindfold",
            "kink",
            "sniff",
            "underwear",
            "anal",
            "challenge",
            "touch yourself",
            "shave",
            "69",
            "dick",
            "pussy",
            "slap",
            "leash",
            "whisper",
            "orgasm",
            "kneel",
            "scratch",
            "slave",
            "master",
            "porn",
            "threesome",
            "foursome",
            "public place",
            "role-play",
            "forced",
            "truth or dare",
            "strip-poker",
            "penetrate",
            "penetrated",
            "slippery",
            "virgin",
            "time",
            "place"
        )
    }
}