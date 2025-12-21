package eu.kanade.tachiyomi.source

import eu.kanade.tachiyomi.source.model.ChaptersPage
import eu.kanade.tachiyomi.source.model.SManga

interface PaginatedChapterListSource {
    /**
     * Get a page of chapters for a manga.
     *
     * @param manga the manga to get chapters for
     * @param page the page number to retrieve (1-indexed)
     * @return a ChaptersPage containing the chapters and pagination info
     */
    suspend fun getChapterList(manga: SManga, page: Int): ChaptersPage
}