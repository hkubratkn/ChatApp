package com.kapirti.ira.ui.presentation.chat.ext

enum class ChatActionOption(val title: String) {
    Block("Block"),
    Report("Report");

    companion object {
        fun getByTitle(title: String): ChatActionOption {
            values().forEach { action -> if (title == action.title) return action }

            return Block
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            values().forEach { chatAction ->
                if (hasEditOption || chatAction != Block) {
                    options.add(chatAction.title)
                }
            }
            return options
        }
    }
}
