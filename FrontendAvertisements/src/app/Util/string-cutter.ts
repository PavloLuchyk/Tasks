export class StringCutter {
  public static descriptionCutter(description: string): string {
    if (description.length > 57) {
      return description.substring(0, 57) + "...";
    } else {
      return description
    }
  }
}
