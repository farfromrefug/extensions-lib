package eu.kanade.tachiyomi.source

import eu.kanade.tachiyomi.source.model.FilterList
import eu.kanade.tachiyomi.source.model.HomePage
import eu.kanade.tachiyomi.source.model.HomeTab
import eu.kanade.tachiyomi.source.model.MangasPage
import rx.Observable

@Suppress("unused")
interface CatalogueSource : Source {

    /**
     * An ISO 639-1 compliant language code (two letters in lower case).
     */
    val lang: String

    /**
     * Whether the source has support for latest updates.
     */
    val supportsLatest: Boolean

    /**
     * Whether the source should show a new extension home screen instead of the default browse screen.
     * When true, the app will display a home page with sections instead of the standard listing.
     *
     * @since extensions-lib TBD
     * @return true if the source should show the new home screen, false otherwise.
     */
    fun shouldShowNewExtensionHome(): Boolean = false

    /**
     * Get the tabs to display on the home screen.
     * If this returns a non-empty list, the home screen will show a tab row and
     * getHomePage() will be called with the selected tab's id.
     * If this returns an empty list (default), no tabs are shown and getHomePage()
     * is called without a tab id (null).
     *
     * @since extensions-lib TBD
     * @return A list of HomeTab objects, or an empty list if no tabs should be shown.
     */
    fun getHomeTabs(): List<HomeTab> = emptyList()

    /**
     * Get the home page with sections of manga.
     * This method should only be called if shouldShowNewExtensionHome() returns true.
     *
     * Note: Sections can optionally start with empty manga lists. If a section has no manga,
     * the app will call getHomeSectionManga() to fetch them lazily when the section becomes visible.
     *
     * @since extensions-lib TBD
     * @param tabId The id of the currently selected tab, or null if no tabs are defined.
     * @return A HomePage object containing sections of manga to display.
     */
    suspend fun getHomePage(tabId: String? = null): HomePage {
        throw UnsupportedOperationException("getHomePage is not supported by this source")
    }

    suspend fun getHomeSectionManga(sectionId: String, page: Int): MangasPage {
        throw UnsupportedOperationException("getHomeSectionManga is not supported by this source")
    }

    /**
     * Returns an observable containing a page with a list of manga.
     *
     * @param page the page number to retrieve.
     */
    fun fetchPopularManga(page: Int): Observable<MangasPage>

    /**
     * Returns an observable containing a page with a list of manga.
     *
     * @param page the page number to retrieve.
     * @param query the search query.
     * @param filters the list of filters to apply.
     */
    fun fetchSearchManga(page: Int, query: String, filters: FilterList): Observable<MangasPage>

    /**
     * Returns an observable containing a page with a list of latest manga updates.
     *
     * @param page the page number to retrieve.
     */
    fun fetchLatestUpdates(page: Int): Observable<MangasPage>

    /**
     * Returns the list of filters for the source.
     */
    fun getFilterList(): FilterList
}