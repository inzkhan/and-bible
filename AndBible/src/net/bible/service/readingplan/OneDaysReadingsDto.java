package net.bible.service.readingplan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.bible.android.BibleApplication;
import net.bible.android.activity.R;

import org.apache.commons.lang.StringUtils;
import org.crosswire.jsword.passage.Key;

/**
 * @author Martin Denham [mjdenham at gmail dot com]
 * @see gnu.lgpl.License for license details.<br>
 *      The copyright to this program is held by it's author.
 */
public class OneDaysReadingsDto implements Comparable<OneDaysReadingsDto> {
	private ReadingPlanInfoDto mReadingPlanInfoDto;
	private int mDay;
	private String mReadings;
	private List<Key> mReadingKeys;
	
	private static final String TAG = "OneDaysReadingsDto";
	
	public OneDaysReadingsDto(int day, String readings, ReadingPlanInfoDto readingPlanInfo) {
		mDay = day;
		mReadings = readings;
		mReadingPlanInfoDto = readingPlanInfo;
	}
	
	@Override
	public String toString() {
		return getDayDesc();
	}

	@Override
	public int compareTo(OneDaysReadingsDto another) {
		return mDay-another.mDay;
	}
	
	public String getDayDesc() {
		return BibleApplication.getApplication().getString(R.string.rdg_plan_day, mDay);
	}

	/** get a string representing the date this reading is planned for
	 */
	public String getReadingDateString() {
		String dateString = "";
		Date startDate = mReadingPlanInfoDto.getStartdate();
		if (startDate!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_MONTH, mDay-1);
			dateString = SimpleDateFormat.getDateInstance().format(cal.getTime());
		}
		return dateString;
	}
	
	public String getReadingsDesc() {
		checkKeysGenerated();
		StringBuffer readingsBuff = new StringBuffer();
		for (int i=0; i<mReadingKeys.size(); i++) {
			if (i>0) {
				readingsBuff.append(", ");
			}
			readingsBuff.append(mReadingKeys.get(i).getName());
		}
		return readingsBuff.toString(); 
	}

	public Key getReadingKey(int no) {
		checkKeysGenerated();
		return mReadingKeys.get(no); 
	}
	public int getNumReadings() {
		checkKeysGenerated();
		return mReadingKeys.size(); 
	}
	
	private synchronized void checkKeysGenerated() {
		if (mReadingKeys==null) {
			List<Key> readingKeyList = new ArrayList<Key>();
			
			if (StringUtils.isNotEmpty(mReadings)) {
				PassageReader passageReader = new PassageReader(mReadingPlanInfoDto.getVersification(), mReadingPlanInfoDto.getPassageReferenceType());
				String[] readingArray = mReadings.split(",");
				for (String reading : readingArray) {
					//use the v11n specified in the reading plan (default is KJV) 
					readingKeyList.add(passageReader.getKey(reading));
				}
			}			
			mReadingKeys = readingKeyList;
		}
	}
	
	public ReadingPlanInfoDto getReadingPlanInfo() {
		return mReadingPlanInfoDto;
	}

	public List<Key> getReadingKeys() {
		checkKeysGenerated();
		return mReadingKeys;
	}

	public int getDay() {
		return mDay;
	}
}
