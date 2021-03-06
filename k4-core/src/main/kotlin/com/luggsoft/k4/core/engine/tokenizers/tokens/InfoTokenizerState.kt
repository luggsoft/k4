package com.luggsoft.k4.core.engine.tokenizers.tokens

import com.luggsoft.k4.core.Source
import com.luggsoft.k4.core.engine.Location
import com.luggsoft.k4.core.engine.tokenizers.TokenizerSettings

class InfoTokenizerState(
    tokenizerSettings: TokenizerSettings
) : TokenizerStateBase(
    tokenizerSettings = tokenizerSettings
)
{
    @Throws(TokenizerStateException::class)
    override fun getNextToken(source: Source, startIndex: Int, tokenizerStateSetter: TokenizerStateSetter): Token
    {
        // initialize
        var index = startIndex
        val stringBuffer = StringBuffer()

        // while not EOF
        while (index < source.text.length)
        {
            // buffer characters
            stringBuffer.append(source.text[index++])

            // if buffer ends with info tag terminator
            if (stringBuffer.endsWith("#>"))
            {
                // discard whitespace, set state to text, and return
                index = this.tryDiscardTrailingLineSeparators(source, index)
                tokenizerStateSetter(TextTokenizerState(this.tokenizerSettings))
                return InfoToken(
                    info = stringBuffer.removeSuffix("#>").toString(),
                    location = Location(
                        name = source.name,
                        startIndex = startIndex,
                        untilIndex = index
                    )
                )
            }
        }

        // EOF was unexpected
        throw this.getUnexpectedEOFTokenizerStateException(source, startIndex, index)
    }
}
