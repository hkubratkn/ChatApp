package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chat.ext

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
            values().forEach { taskAction ->
                if (hasEditOption || taskAction != Block) {
                    options.add(taskAction.title)
                }
            }
            return options
        }
    }
}
