/*
 * Created on Mar 28, 2005
 */
package zz.utils.notification;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;

/**
 * A basic implementation of {@link zz.utils.notification.IEvent}
 * @author gpothier
 */
public class SimpleEvent<T> extends AbstractEvent<T>
{
	private List<IEventListener<? super T>> itsListeners;

	public void addListener(IEventListener< ? super T> aListener)
	{
		if (itsListeners == null) itsListeners = new ArrayList<IEventListener<? super T>>(3);
		itsListeners.add (aListener);
	}

	public void removeListener(IEventListener< ? super T> aListener)
	{
		if (itsListeners != null) itsListeners.remove(aListener);
	}
	
	/**
	 * Notifies all listeners that the event has been fired.
	 * @param aData An object to pass to the listeners.
	 */
	public void fire (T aData)
	{
		if (itsListeners != null) for (IEventListener<? super T> theListener : itsListeners)
		{
			theListener.fired(this, aData);
		}
	}

	/**
	 * Returns an event object that is fired whenever the specified property changes.
	 */
	public static <T> SimpleEvent<T> createEvent (IProperty<T> aProperty)
	{
		return new PropertyChangeEvent<T>(aProperty);
	}

	/**
	 * This class represents an event this is fired whenever a given property changes.
	 * @author gpothier
	 */
	private static class PropertyChangeEvent<T> extends SimpleEvent<T>
	implements IPropertyListener<T>
	{
		public PropertyChangeEvent(IProperty<T> aProperty)
		{
			aProperty.addListener(this);
		}

		public void propertyChanged(IProperty<T> aProperty, T aOldValue, T aNewValue)
		{
			fire(aNewValue);
		}

		public void propertyValueChanged(IProperty<T> aProperty)
		{
			fire(aProperty.get());
		}
	}
}