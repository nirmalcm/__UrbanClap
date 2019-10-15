package com.webcreo.nirmal.mukthi.temp.model;

import android.content.Context;

import com.webcreo.nirmal.mukthi.temp.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelBase implements Serializable {

    private String id;
    private String path;

    private String name;
    private String description;

    private String icon;
    private String iconPath;
    private String iconUrl;
    private int iconDrawable;

    private String image;
    private String imagePath;
    private String imageUrl;
    private int imageDrawable;

    private String background;
    private String backgroundPath;
    private String backgroundUrl;
    private int backgroundDrawable;

    public ModelRealEstate modelRealEstate;
    public ModelHomeService modelHomeService;
    public ModelConstructionService modelConstructionService;

    private List<ModelBase> subCategories;

    private boolean isMore = false;

    private boolean hasId =false;
    private boolean hasPath = false;

    private boolean hasName = false;
    private boolean hasDescription = false;

    private boolean hasIcon = false;
    private boolean hasIconDrawable = false;
    private boolean hasIconPath = false;
    private boolean hasIconUrl = false;

    private boolean hasImage = false;
    private boolean hasImageDrawable = false;
    private boolean hasImagePath = false;
    private boolean hasImageUrl = false;

    private boolean hasBackground = false;
    private boolean hasBackgroundDrawable = false;
    private boolean hasBackgroundPath = false;
    private boolean hasBackgroundUrl = false;

    private boolean hasSubCategories = false;

    public ModelBase()
    {
        modelHomeService = new ModelHomeService();
        modelRealEstate = new ModelRealEstate();
        modelConstructionService = new ModelConstructionService();
    }

    private ArrayList<ModelBase> getContents(JSONArray jsonArray, Context context) {
        ArrayList<ModelBase> categories = new ArrayList<>();
        try {
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ModelBase category = new ModelBase();

                try {
                    boolean isMore = jsonObject.getBoolean("isMore");
                    category.setIsMore(isMore);
                }
                catch (Exception e){

                }

                try {
                    String id = jsonObject.getString("image");
                    if (!id.isEmpty()){
                        category.setId(id);
                    }
                }
                catch (Exception e){

                }

                try {
                    String path = jsonObject.getString("path");
                    if (!path.isEmpty()){
                        category.setPath(path);
                    }
                }
                catch (Exception e){

                }

                try {
                    String name = jsonObject.getString("name");
                    if (!name.isEmpty()){
                        category.setName(name);
                    }
                }
                catch (Exception e){

                }

                try {
                    String description = jsonObject.getString("description");
                    if (!description.isEmpty()){
                        category.setDescription(description);
                    }
                }
                catch (Exception e){

                }

                try {
                    String image = jsonObject.getString("image");
                    if(!image.isEmpty()){
                        category.setImage(image);

                        try {
                            String imagePath = jsonObject.getString("image_path");
                            if (!imagePath.isEmpty()){
                                category.setImagePath(imagePath);
                            }
                        }
                        catch (Exception e) {
                        }

                        try {
                            String imageUrl = jsonObject.getString("image_url");
                            if (!imageUrl.isEmpty()){
                                category.setImageUrl(imageUrl);
                            }
                        }
                        catch (Exception e) {
                        }

                        String imageWithoutExtension = Tools.removeFileExtension(image);
                        int imageDrawable = context.getResources().getIdentifier(imageWithoutExtension,"drawable",context.getPackageName());
                        category.setImageDrawable(imageDrawable);
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                }

                try {
                    String background = jsonObject.getString("background");
                    if (!background.isEmpty()){
                        category.setBackground(background);

                        try {
                            String backgroundPath = jsonObject.getString("background_path");
                            if (!backgroundPath.isEmpty()){
                                category.setBackgroundPath(backgroundPath);
                            }
                        }
                        catch (Exception e) {
                        }

                        try {
                            String backgroundUrl = jsonObject.getString("background_url");
                            if (!backgroundUrl.isEmpty()){
                                category.setBackgroundUrl(backgroundUrl);
                            }
                        }
                        catch (Exception e) {
                        }

                        int backgroundDrawable = context.getResources().getIdentifier(Tools.removeFileExtension(background),"drawable",context.getPackageName());
                        category.setBackgroundDrawable(backgroundDrawable);
                    }
                }
                catch (Exception e) {
                }

                try {
                    String icon = jsonObject.getString("icon");
                    if (!icon.isEmpty()){
                        category.setIcon(icon);

                        try {
                            String iconPath = jsonObject.getString("icon_path");
                            if (!iconPath.isEmpty()){
                                category.setIconPath(iconPath);
                            }
                        }
                        catch (Exception e) {
                        }

                        try {
                            String iconUrl = jsonObject.getString("icon_url");
                            if (!iconUrl.isEmpty()){
                                category.setIconUrl(iconUrl);
                            }
                        }
                        catch (Exception e) {
                        }

                        int iconDrawable = context.getResources().getIdentifier(Tools.removeFileExtension(icon),"drawable",context.getPackageName());
                        category.setIconDrawable(iconDrawable);
                    }
                }
                catch (Exception e) {
                }

                try {
                    String location = jsonObject.getString("location");
                    if (!location.isEmpty()){
                        category.modelRealEstate.setLocation(location);
                    }
                }
                catch (Exception e){

                }

                try {
                    String score = jsonObject.getString("score");
                    if (!score.isEmpty()){
                        category.modelRealEstate.setScore(score);
                    }
                }
                catch (Exception e){

                }

                try {
                    String type = jsonObject.getString("type");
                    if (!type.isEmpty()){
                        category.modelRealEstate.setType(type);
                    }
                }
                catch (Exception e){

                }

                try {
                    String price = jsonObject.getString("price");
                    if (!price.isEmpty()){
                        category.modelRealEstate.setPrice(price);
                    }
                }
                catch (Exception e){

                }

                try {
                    String size = jsonObject.getString("size");
                    if (!size.isEmpty()){
                        category.modelRealEstate.setSize(size);
                    }
                }
                catch (Exception e){

                }

                try {
                    boolean isRealEstate = jsonObject.getBoolean("is_real_estate");
                }
                catch (Exception e){

                }

                try {
                    JSONArray children = jsonObject.getJSONArray("children");
                    category.setSubCategories(getContents(children,context));
                }
                catch (JSONException e){
                }

                categories.add(category);
            }
        }
        catch (JSONException e){

        }
        return categories;
    }

    private JSONArray getJSONContents(String fileName, String categoryName, Context context){
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(Tools.loadJSONFromAsset(fileName, context));
            jsonArray = jsonObject.getJSONArray(categoryName);
        }
        catch (JSONException e){

        }
        return jsonArray;
    }

    public ArrayList<ModelBase> getContents(Context context, String fileName, String contentName, boolean isMoreRequired){
        JSONArray jsonArray = getJSONContents(fileName,contentName,context);
        ArrayList<ModelBase> contents = getContents(jsonArray,context);
        if(!isMoreRequired){
            for (ModelBase category:contents){
                if(category.isMore()){
                    contents.remove(category);
                }
            }
        }
        return contents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.hasId = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.hasName = true;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
        this.hasImage = true;
    }

    public List<ModelBase> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<ModelBase> subCategories) {
        this.subCategories = subCategories;
        this.hasSubCategories = true;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setIsMore(boolean isMore) {
        this.isMore = isMore;
    }

    public boolean isHasId() {
        return hasId;
    }

    public boolean isHasName() {
        return hasName;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public boolean isHasSubCategories() {
        return hasSubCategories;
    }

    public int getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(int backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
        this.hasBackgroundDrawable = true;
    }

    public boolean isHasBackground() {
        return hasBackground;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        this.hasImage = true;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
        this.hasBackground = true;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        this.hasIcon = true;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        this.hasPath = true;
    }

    public boolean isHasPath() {
        return hasPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.hasDescription = true;
    }

    public boolean isHasDescription() {
        return hasDescription;
    }

    public int getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(int iconDrawable) {
        this.iconDrawable = iconDrawable;
        this.hasIconDrawable = true;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.hasImageUrl = true;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        this.hasIconUrl = true;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
        this.hasBackgroundUrl = true;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.hasImagePath = true;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
        this.hasIconPath = true;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
        this.hasBackgroundPath = true;
    }

    public boolean isHasImageDrawable() {
        return hasImageDrawable;
    }

    public boolean isHasImagePath() {
        return hasImagePath;
    }

    public boolean isHasImageUrl() {
        return hasImageUrl;
    }

    public boolean isHasIconDrawable() {
        return hasIconDrawable;
    }

    public boolean isHasIconPath() {
        return hasIconPath;
    }

    public boolean isHasIconUrl() {
        return hasIconUrl;
    }

    public boolean isHasBackgroundDrawable() {
        return hasBackgroundDrawable;
    }

    public boolean isHasBackgroundPath() {
        return hasBackgroundPath;
    }

    public boolean isHasBackgroundUrl() {
        return hasBackgroundUrl;
    }
}