package com.couplesdating.couplet.domain.useCase.idea

class DecorateIdeaUseCaseImpl : DecorateIdeaUseCase {

    override fun getIdeasToDecorate(description: String): List<String>? {
        return WORDS_TO_DECORATE.filter { word ->
            description.contains(word)
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
            "niples",
            "lick",
            "suck",
            "spank",
            "suffocate",
            "tie up",
            "butthole",
            "genitals",
            "jerk off",
            "dildo",
            "strip-tease",
            "golden shower",
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
            "roleplay",
            "forced",
            "truth or dare",
            "strip-poker",
            "penetrate",
            "penetrated"
        )
    }
}