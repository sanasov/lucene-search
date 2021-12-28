package ru.igrey.dev

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import java.io.IOException

class AnalyzatorService {

    companion object {
        @Throws(IOException::class)
        fun analyze(text: String, analyzer: Analyzer): List<String> {
            val result: MutableList<String> = ArrayList()
            val tokenStream: TokenStream = analyzer.tokenStream(FIELD_NAME, text)
            val attr: CharTermAttribute = tokenStream.addAttribute(CharTermAttribute::class.java)
            tokenStream.reset()
            while (tokenStream.incrementToken()) {
                result.add(attr.toString())
            }
            return result
        }
    }
}

fun main() {
    println(AnalyzatorService.analyze(SAMPLE_TEXT, StandardAnalyzer()))
}

private const val SAMPLE_TEXT = "This is baeldung.com Lucene Analyzers test"
private const val FIELD_NAME = "sampleName"