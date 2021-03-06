package com.uwsoft.editor.renderer.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.uwsoft.editor.renderer.IResource;
import com.uwsoft.editor.renderer.data.SelectBoxVO;

public class SelectBoxItem<T> extends SelectBox<T> implements IBaseItem {
	
	public SelectBoxVO dataVO;	
	public IResource rm;
	public float mulX = 1f;
	public float mulY = 1f;
	protected int layerIndex = 0;
	private boolean isLockedByLayer = false;
	private CompositeItem parentItem = null;
	
	public SelectBoxItem(SelectBoxVO vo, IResource rm,CompositeItem parent) {
		this(vo, rm);
		setParentItem(parent);
	}
	
	public SelectBoxItem(SelectBoxVO vo, IResource rm) {
		super(rm.getSkin(),vo.style.isEmpty()?"default":vo.style);
		dataVO = vo;	
		this.rm = rm;
		setX(dataVO.x);
		setY(dataVO.y);
		setScaleX(dataVO.scaleX);
		setScaleY(dataVO.scaleY);
		this.setRotation(dataVO.rotation); 
		
		if(dataVO.zIndex < 0) dataVO.zIndex = 0;
				
		if(dataVO.tint == null) {			
			setTint(new Color(1, 1, 1, 1));	
		} else {
			setTint(new Color(dataVO.tint[0], dataVO.tint[1], dataVO.tint[2], dataVO.tint[3]));
		}
	}	
	
	public void setTint(Color tint) {
		float[] clr = new float[4]; 
		clr[0] = tint.r;
		clr[1] = tint.g;
		clr[2] = tint.b;
		clr[3] = tint.a;
		this.getDataVO().tint = clr;
		this.setColor(tint);
	}
		
	public SelectBoxVO getDataVO() {
		//updateDataVO();
		return dataVO;
	}
	
	@Override
	public void renew() {		
		pack(); layout();
		if(dataVO.width > 0) {
			setWidth(dataVO.width);
		}
		if(dataVO.height > 0) {
			setHeight(dataVO.height);
		}
		
		setX(dataVO.x*this.mulX);
		setY(dataVO.y*this.mulY);
		setScaleX(dataVO.scaleX*this.mulX);
		setScaleY(dataVO.scaleY*this.mulY);
		setRotation(dataVO.rotation); 
	}
	
	@Override
	public boolean isLockedByLayer() {
		return isLockedByLayer;
	}

	@Override
	public void setLockByLayer(boolean isLocked) {
		isLockedByLayer = isLocked;
	}

	@Override
	public boolean isComposite() {
		return false;
	}
	
	public void updateDataVO() {
		
		dataVO.x = getX()/this.mulX;
		dataVO.y = getY()/this.mulY;
		dataVO.rotation = getRotation();
		
		if(getZIndex()>=0){
			dataVO.zIndex = getZIndex();
		}
		
		if(dataVO.layerName == null || dataVO.layerName.equals("")) {
			dataVO.layerName = "Default";
		}
	}

	public void applyResolution(float mulX, float mulY) {
		setScaleX(dataVO.scaleX*mulX);
		setScaleY(dataVO.scaleY*mulY);
		this.mulX = mulX;
		this.mulY = mulY;
		setX(dataVO.x*this.mulX);
		setY(dataVO.y*this.mulY);
		updateDataVO();	
	}
	
	@Override
	public int getLayerIndex() {
		return layerIndex;
	}

	@Override
	public void setLayerIndex(int index) {
		layerIndex = index;
	}
	
	public CompositeItem getParentItem() {
		return parentItem;
	}
	
	public void setParentItem(CompositeItem parentItem) {
		this.parentItem = parentItem;
	}
	
	public void setStyle(SelectBoxStyle lst, String styleName) {
		setStyle(lst);
		dataVO.style	=	styleName;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
