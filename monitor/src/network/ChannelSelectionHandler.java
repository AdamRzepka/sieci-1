package network;

import java.nio.channels.SelectableChannel;

public interface ChannelSelectionHandler {
	public void onSelected(SelectableChannel channel, int readyOperationsMask);
}
