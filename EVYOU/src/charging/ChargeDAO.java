package charging;

import org.w3c.dom.Element;

public interface ChargeDAO
{
	public void refresh();
	public String getTagValue(String tag, Element eElement);
}