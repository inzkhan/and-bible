package net.bible.android.control.page;

import net.bible.android.control.versification.ConvertibleVerse;

import org.crosswire.jsword.passage.Verse;
import org.crosswire.jsword.versification.BibleBook;
import org.crosswire.jsword.versification.Versification;
import org.crosswire.jsword.versification.system.Versifications;

public class CurrentBibleVerse {
	
	private ConvertibleVerse verseVersificationSelected = new ConvertibleVerse(Versifications.instance().getVersification(Versifications.DEFAULT_V11N), BibleBook.GEN, 1, 1);

	public int getCurrentBibleBookNo() {
		return verseVersificationSelected.getBook().ordinal();
	}

	public BibleBook getCurrentBibleBook() {
		return verseVersificationSelected.getBook();
	}
	
	public Verse getVerseSelected(Versification versification) {
		return verseVersificationSelected.getVerse(versification);
	}
	public void setVerseSelected(Versification versification, Verse verseSelected) {
		verseVersificationSelected.setVerse(versification, verseSelected);
	}
	public void setVerseNo(int verseNo) {
		verseVersificationSelected.setVerseNo(verseNo);
	}
	public int getVerseNo() {
		return verseVersificationSelected.getVerseNo();
	}
}
