package com.cy.pictool.component;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import com.cy.pictool.action.FrameAction;

public final class PathDragDropListener implements DropTargetListener {

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		
		
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		
		 // Accept copy drops
        event.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    @SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(flavor);

                    // Loop them through
                    for (File file : files) {

                    	if (file.isDirectory()){
                    		FrameAction.getInstance().setPath(file.getPath(), -1);
                    		break;
                    	}
                        // Print out the file path
//                        System.out.println("File path is '" + file.getPath() + "'.");

                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }

        // Inform that the drop is complete
        event.dropComplete(true);
	}

}
