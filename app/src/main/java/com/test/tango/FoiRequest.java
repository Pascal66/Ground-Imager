package com.test.tango;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.atap.tangoservice.experimental.TangoTransformation;

import java.util.Arrays;

public abstract class FoiRequest implements Parcelable {
	public Type mType;
	public String mId;
	public static final Creator<FoiRequest> CREATOR = new Creator<FoiRequest>() {
		public FoiRequest createFromParcel(Parcel in) {
			Type type = Type.fromInt(in.readInt());
			if (type == null) {
				return null;
			} else {
				String id = in.readString();
				if (id == null) {
					return null;
				} else {
					FoiRequest result = null;
					switch(type) {
						case CREATE:
							result = new Create();
							break;
						case LOAD:
							result = new Load();
							break;
						case DELETE:
							result = new Delete();
							break;
						default:
							return null;
					}

					((FoiRequest)result).mType = type;
					((FoiRequest)result).mId = id;
					((FoiRequest)result).parcelRead(in);
					return (FoiRequest)result;
				}
			}
		}

		public FoiRequest[] newArray(int size) {
			return new FoiRequest[size];
		}
	};

	protected abstract void parcelRead(Parcel var1);

	protected abstract void parcelWrite(Parcel var1);

	private FoiRequest(Type type) {
		this.mType = Type.INVALID;
		this.mId = "";
		this.mType = type;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mType.ordinal());
		dest.writeString(this.mId);
		this.parcelWrite(dest);
	}

	public static class Delete extends FoiRequest {
		public String[] mFrameIds = null;

		public Delete() {
			super(Type.DELETE/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == Delete.class) {
				Delete other = (Delete)obj;
				boolean var10000;
				if (this.mType == other.mType) {
					label22: {
						if (this.mId != null) {
							if (!this.mId.equals(other.mId)) {
								break label22;
							}
						} else if (other.mId != null) {
							break label22;
						}

						if (Arrays.equals(this.mFrameIds, other.mFrameIds)) {
							var10000 = true;
							return var10000;
						}
					}
				}

				var10000 = false;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mFrameIds = in.createStringArray();
		}

		protected void parcelWrite(Parcel dest) {
			dest.writeStringArray(this.mFrameIds);
		}
	}

	public static class Load extends FoiRequest {
		public String[] mFrameIds = null;

		public Load() {
			super(Type.LOAD/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == Load.class) {
				Load other = (Load)obj;
				boolean var10000;
				if (this.mType == other.mType) {
					label22: {
						if (this.mId != null) {
							if (!this.mId.equals(other.mId)) {
								break label22;
							}
						} else if (other.mId != null) {
							break label22;
						}

						if (Arrays.equals(this.mFrameIds, other.mFrameIds)) {
							var10000 = true;
							return var10000;
						}
					}
				}

				var10000 = false;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mFrameIds = in.createStringArray();
		}

		protected void parcelWrite(Parcel dest) {
			dest.writeStringArray(this.mFrameIds);
		}
	}

	public static class Create extends FoiRequest {
		public double mTimestamp = 0.0D;
		public String mBaseFrameId = null;
		public TangoTransformation mTransformation = null;

		public Create() {
			super(Type.CREATE/*, null*/);
		}

		public boolean equals(Object obj) {
			if (obj != null && obj.getClass() == Create.class) {
				boolean var10000;
				label56: {
					Create other = (Create)obj;
					if (this.mType == other.mType) {
						label49: {
							if (this.mId != null) {
								if (!this.mId.equals(other.mId)) {
									break label49;
								}
							} else if (other.mId != null) {
								break label49;
							}

							if (this.mTimestamp == other.mTimestamp) {
								label50: {
									if (this.mBaseFrameId != null) {
										if (!this.mBaseFrameId.equals(other.mBaseFrameId)) {
											break label50;
										}
									} else if (other.mBaseFrameId != null) {
										break label50;
									}

									if (this.mTransformation != null) {
										if (this.mTransformation.equals(other.mTransformation)) {
											break label56;
										}
									} else if (other.mTransformation == null) {
										break label56;
									}
								}
							}
						}
					}

					var10000 = false;
					return var10000;
				}

				var10000 = true;
				return var10000;
			} else {
				return false;
			}
		}

		protected void parcelRead(Parcel in) {
			this.mTimestamp = in.readDouble();
			this.mBaseFrameId = in.readString();
			if (in.readInt() != 0) {
				this.mTransformation = (TangoTransformation)TangoTransformation.CREATOR.createFromParcel(in);
			} else {
				this.mTransformation = null;
			}

		}

		protected void parcelWrite(Parcel dest) {
			dest.writeDouble(this.mTimestamp);
			dest.writeString(this.mBaseFrameId);
			if (this.mTransformation != null) {
				dest.writeInt(1);
				this.mTransformation.writeToParcel(dest, 0);
			} else {
				dest.writeInt(0);
			}

		}
	}

	public static enum Type {
		INVALID,
		CREATE,
		LOAD,
		DELETE;

		private static final Type[] values = values();

		private Type() {
		}

		public static Type fromInt(int ordinal) {
			return ordinal >= 0 && ordinal < values.length ? values[ordinal] : null;
		}
	}
}
